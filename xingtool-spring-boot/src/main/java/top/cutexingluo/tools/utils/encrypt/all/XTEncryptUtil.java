package top.cutexingluo.tools.utils.encrypt.all;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Formatter;

/**
 * 加密工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/9/17 16:36
 */
public class XTEncryptUtil {
    public enum EncType {
        /**
         * MD5
         */
        MD5,
        /**
         * SHA-256
         */
        SHA256("SHA-256"),
        /**
         * DES
         */
        DES,
        /**
         * AES
         */
        AES,
        /**
         * RSA
         */
        RSA,
        ;


        EncType() {
            this.name = super.name();
        }

        EncType(String name) {
            this.name = name;
        }

        private final String name;

        public String getName() {
            return name;
        }
    }

    public static class Md5 {
        public static Md5 newInstance() {
            return new Md5();
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Meta {
            private String salt;
            private int index = 0;
            private int count = 1;
        }

        public enum ALGO {
            /**
             * HuTool 加密，默认模式
             */
            Default,
            /**
             * security 原生加密
             */
            Security,
            /**
             * 盐值加密
             */
            Salt,
            /**
             * 随机20个字符盐加密并拼接
             */
            Random20Salt,
        }

        /**
         * 编码/加密
         *
         * @param data 数据
         * @param algo 算法
         * @param meta 元数据，只有 salt 有用
         * @return {@link String}
         */
        public String encode(String data, ALGO algo, Meta meta) {
            switch (algo) {
                case Security:
                    return encodeBySecurity(data);
                case Salt:
                    return encodeBySalt(data, meta);
                case Random20Salt:
                    return encodeBySaltRandom(data);
                default:
                    return encodeHex(data);
            }
        }

        /**
         * 验证是否一致
         *
         * @param data      数据
         * @param encrypted 已加密数据
         * @param algo      算法
         * @param meta      元数据，只有 salt 有用
         * @return boolean
         */
        public boolean validate(@NotNull String data, String encrypted, ALGO algo, Meta meta) {
            switch (algo) {
                case Security:
                    return encodeBySecurity(data).equals(encrypted);
                case Salt:
                    return encodeBySalt(data, meta).equals(encrypted);
                case Random20Salt:
                    return validateBySaltRandom(data, encrypted);
                default:
                    return encodeHex(data).equals(encrypted);
            }
        }

        public String encodeHex(String data) {
            return DigestUtil.md5Hex(data);
        }

        public byte[] encode(String data) {
            return DigestUtil.md5(data);
        }

        public String encodeBySecurity(String data) {
            MessageDigest messageDigest;
            byte[] digest = new byte[0];
            try {
                messageDigest = MessageDigest.getInstance(EncType.MD5.getName());
                digest = messageDigest.digest(data.getBytes());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            Formatter formatter = new Formatter();
            for (byte b : digest) { // 补齐前导0
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }

        /**
         * 按SALT编码
         *
         * @param data 数据
         * @param meta 盐
         * @return {@link String}
         */
        public String encodeBySalt(String data, Meta meta) {
            if (StrUtil.isBlank(data)) {
                return data;
            }
            if (StrUtil.isBlank(meta.getSalt())) {
                return encodeHex(data);
            }
            MD5 md5 = new MD5(meta.getSalt().getBytes(StandardCharsets.UTF_8),
                    meta.getIndex(), meta.getCount());
            // 返回16进制格式
            return md5.digestHex(data);
        }


        private static final int SALT_BYTE_LENGTH = 20;

        /**
         * 按随机SALT编码
         *
         * @param data 数据
         * @return {@link String}
         */
        public String encodeBySaltRandom(String data) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[SALT_BYTE_LENGTH];
            secureRandom.nextBytes(salt);
            MD5 md5 = new MD5(salt);
            byte[] digest = md5.digest(data, StandardCharsets.UTF_8);
            //填充前20个字节为盐值，校验密码时候需要取出
            byte[] pwd = ArrayUtil.addAll(salt, digest);
            return HexUtil.encodeHexStr(pwd);
        }

        /**
         * 按随机SALT验证
         *
         * @param data      数据
         * @param encrypted 已加密
         * @return boolean
         */
        public boolean validateBySaltRandom(String data, String encrypted) {
            byte[] encryptedPwd = HexUtil.decodeHex(encrypted);
            //取出前20个字节盐值
            byte[] salt = ArrayUtil.sub(encryptedPwd, 0, SALT_BYTE_LENGTH);
            //20字节后为真正MD5后密码
            byte[] pwd = ArrayUtil.sub(encryptedPwd, SALT_BYTE_LENGTH, encryptedPwd.length);
            MD5 md5 = new MD5(salt);
            byte[] digest = md5.digest(data, StandardCharsets.UTF_8);
            return Arrays.equals(digest, pwd);
        }
    }

    //----------------------------------------------------------------

    public static class SHA256 {
        public static SHA256 newInstance() {
            return new SHA256();
        }

        public enum ALGO {
            /**
             * HuTool 加密，默认模式
             */
            Default,
            /**
             * security 原生加密
             */
            Security,
        }

        public String encode(String data, ALGO algo) {
            if (algo == ALGO.Security) {
                return encodeBySecurity(data);
            }
            return DigestUtil.sha256Hex(data);
        }

        public boolean validate(String data, String encrypted, ALGO algo) {
            if (algo == ALGO.Security) {
                return encodeBySecurity(data).equals(encrypted);
            }
            return encodeHex(data).equals(encrypted);
        }

        public String encodeHex(String data) {
            return DigestUtil.sha256Hex(data);
        }


        public String encodeBySecurity(String data) {
            //获取SHA-256算法实例
            MessageDigest messageDigest = null;
            byte[] digest = new byte[0];
            try {
                messageDigest = MessageDigest.getInstance(EncType.SHA256.getName());
                //计算散列值
                digest = messageDigest.digest(data.getBytes());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            //将byte数组转换为16进制字符串
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return stringBuilder.toString();
        }
    }
    //----------------------------------------------------------------

    public static class X_DES {
        public static X_DES newInstance() {
            return new X_DES();
        }

        public enum ALGO {
            /**
             * HuTool 加密，默认模式
             */
            Default,
            /**
             * security 原生加密
             */
            Security,
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Meta {
            private Mode mode = Mode.ECB;
            private String iv;
        }

        /**
         * DES加密
         *
         * @param data 待加密的数据
         * @param key  密钥，长度必须为8位
         * @param meta 元数据，可以设置模式
         * @return 加密后的数据，使用Base64编码
         */
        public String encode(String data, String key, @Nullable Meta meta) {
            if (meta == null) meta = new Meta();
            DES des = new DES(meta.getMode(), Padding.PKCS5Padding, key.getBytes(), meta.getIv().getBytes());
            return des.encryptBase64(data);
        }

        /**
         * DES解密
         *
         * @param encryptedData 加密后的数据，使用Base64编码
         * @param key           密钥，长度必须为8位
         * @param meta          元数据，可以设置模式
         * @return 解密后的数据
         */
        public String decode(String encryptedData, String key, @Nullable Meta meta) {
            if (meta == null) meta = new Meta();
            DES des = new DES(meta.getMode(), Padding.PKCS5Padding, key.getBytes(), meta.getIv().getBytes());
            return des.decryptStr(encryptedData);
        }

        /**
         * DES加密
         *
         * @param data 待加密的数据
         * @param key  密钥，长度必须为8位
         * @return 加密后的数据，使用Base64编码
         */
        public String encodeBySecurity(String data, String key)
                throws Exception {
            // 根据密钥生成密钥规范
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            // 根据密钥规范生成密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(EncType.DES.getName());
            // 根据密钥工厂和密钥规范生成密钥
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            // 根据加密算法获取加密器
            Cipher cipher = Cipher.getInstance(EncType.DES.getName());
            // 初始化加密器，设置加密模式和密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 加密数据
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            // 对加密后的数据进行Base64编码
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * DES解密
         *
         * @param encryptedData 加密后的数据，使用Base64编码
         * @param key           密钥，长度必须为8位
         * @return 解密后的数据
         */
        public String decodeBySecurity(String encryptedData, String key) throws Exception {
            // 根据密钥生成密钥规范
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            // 根据密钥规范生成密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(EncType.DES.getName());
            // 根据密钥工厂和密钥规范生成密钥
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            // 对加密后的数据进行Base64解码
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            // 根据加密算法获取解密器
            Cipher cipher = Cipher.getInstance(EncType.DES.getName());
            // 初始化解密器，设置解密模式和密钥
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 解密数据
            byte[] decryptedData = cipher.doFinal(decodedData);
            // 将解密后的数据转换为字符串
            return new String(decryptedData);
        }
    }
    //----------------------------------------------------------------

    public static class X_AES {
        public static X_AES newInstance() {
            return new X_AES();
        }

        public enum ALGO {
            /**
             * HuTool 加密，默认模式
             */
            Default,
            /**
             * security 原生加密
             */
            Security,
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Meta {
            private Mode mode = Mode.ECB;
            /**
             * 密钥为16位
             */
            private String key;
            /**
             * 向量为16位
             */
            private String iv;

            public String getTransformation() {
                if (mode == Mode.CBC) {
                    return AES_CBC_TRANSFORMATION;
                } else if (mode == Mode.ECB) {
                    return AES_ECB_TRANSFORMATION;
                }
                return "AES/" + mode.name() + "/PKCS5Padding";
            }

            // AES加密模式为CBC，填充方式为PKCS5Padding
            private static final String AES_CBC_TRANSFORMATION = "AES/CBC/PKCS5Padding";
            private static final String AES_ECB_TRANSFORMATION = "AES/ECB/PKCS5Padding";
        }

        /**
         * AES加密
         *
         * @param data 待加密的数据
         * @param key  密钥，长度必须为8位
         * @param meta 元数据，可以设置模式
         * @return 加密后的数据，使用Base64编码
         */
        public String encode(String data, String key, @Nullable Meta meta) {
            if (meta == null) meta = new Meta();
            AES aes = new AES(meta.getMode(), Padding.PKCS5Padding, key.getBytes(), meta.getIv().getBytes());
            return aes.encryptBase64(data);
        }

        /**
         * AES解密
         *
         * @param encryptedData 加密后的数据，使用Base64编码
         * @param key           密钥，长度必须为8位
         * @param meta          元数据，可以设置模式
         * @return 解密后的数据
         */
        public String decode(String encryptedData, String key, @Nullable Meta meta) {
            if (meta == null) meta = new Meta();
            AES aes = new AES(meta.getMode(), Padding.PKCS5Padding, key.getBytes(), meta.getIv().getBytes());
            return aes.decryptStr(encryptedData);
        }

        /**
         * AES加密
         *
         * @param data 待加密的数据
         * @return 加密后的数据，使用Base64编码
         */
        public String encodeBySecurity(String data, Meta meta) throws Exception {
            // 将AES密钥转换为SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(meta.getKey().getBytes(), EncType.AES.getName());
            // 将AES初始化向量转换为IvParameterSpec对象
            IvParameterSpec ivParameterSpec = new IvParameterSpec(meta.getIv().getBytes());
            // 根据加密算法获取加密器
            Cipher cipher = Cipher.getInstance(meta.getTransformation());
            // 初始化加密器，设置加密模式、密钥和初始化向量
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            // 加密数据
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            // 对加密后的数据使用Base64编码
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * AES解密
         *
         * @param encryptedData 加密后的数据，使用Base64编码
         * @param meta          梅塔
         * @return 解密后的数据
         * @throws Exception
         */
        public String decodeBySecurity(String encryptedData, Meta meta) throws Exception {
            // 将AES密钥转换为SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(meta.getKey().getBytes(), EncType.AES.getName());
            // 将AES初始化向量转换为IvParameterSpec对象
            IvParameterSpec ivParameterSpec = new IvParameterSpec(meta.getKey().getBytes());
            // 根据加密算法获取解密器
            Cipher cipher = Cipher.getInstance(meta.getTransformation());
            // 初始化解密器，设置解密模式、密钥和初始化向量
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            // 对加密后的数据使用Base64解码
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            // 解密数据
            byte[] decryptedData = cipher.doFinal(decodedData);
            // 返回解密后的数据
            return new String(decryptedData, StandardCharsets.UTF_8);
        }

    }

    //----------------------------------------------------------------
    public static class X_RSA {
        public static X_RSA newInstance() {
            return new X_RSA();
        }

        public enum ALGO {
            /**
             * HuTool 加密，默认模式
             */
            Default,
            /**
             * security 原生加密
             */
            Security,
        }

        @Data
        public static class Meta {
            KeyPair keyPair;
            Mode mode = Mode.ECB;

            public Meta(KeyPair keyPair) {
                this.keyPair = keyPair;
            }


            public String getTransformation() {
                if (mode == Mode.ECB) {
                    return AES_ECB_TRANSFORMATION;
                }
                return "RSA/" + mode.name() + "/PKCS1Padding";
            }

            private static final String AES_ECB_TRANSFORMATION = AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue();
        }

        /**
         * 生成RSA密钥对
         *
         * @return RSA密钥对
         */
        public KeyPair generateKeyPair() {
            return SecureUtil.generateKeyPair(EncType.RSA.getName());
        }

        /**
         * 生成RSA密钥对
         *
         * @return RSA密钥对
         */
        public KeyPair generateKeyPairBySecurity() {
            KeyPairGenerator keyPairGenerator = null;
            try {
                keyPairGenerator = KeyPairGenerator.getInstance(EncType.RSA.getName());
                keyPairGenerator.initialize(2048); // 密钥大小为2048位
                return keyPairGenerator.generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return generateKeyPair();
        }


        /**
         * 按Base64编码密钥
         *
         * @param keyPair 密钥对
         * @param keyType 密钥类型
         * @return {@link String}
         */
        public String getEncodeKeyByBase64(KeyPair keyPair, KeyType keyType) {
            if (keyType == KeyType.PrivateKey) {
                return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            }
            return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        }

        /**
         * 使用Base64获取解码密钥
         *
         * @param base64String Base64字符串
         * @param keyType      密钥类型
         * @return {@link T}
         */
        public <T extends Key> T getDecodeKeyByBase64(String base64String, KeyType keyType) {
            if (keyType == KeyType.PublicKey) {
                byte[] keyBytes = Base64.getDecoder().decode(base64String);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = null;
                PublicKey publicKey = null;
                try {
                    keyFactory = KeyFactory.getInstance(EncType.RSA.getName());
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                return (T) publicKey;
            } else if (keyType == KeyType.PrivateKey) {
                byte[] keyBytes = Base64.getDecoder().decode(base64String);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = null;
                PrivateKey privateKey = null;
                try {
                    keyFactory = KeyFactory.getInstance(EncType.RSA.getName());
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                return (T) privateKey;
            }
            return null;
        }

        /**
         * 获取PrivateKey
         */
        public PrivateKey getPrivateKeyByBase64(String base64String) {
            return getDecodeKeyByBase64(base64String, KeyType.PrivateKey);
        }

        /**
         * 获取PublicKey
         */
        public PublicKey getPublicKeyByBase64(String base64String) {
            return getDecodeKeyByBase64(base64String, KeyType.PublicKey);
        }


        /**
         * 从元数据获取rsa
         *
         * @return {@link RSA}
         */
        public RSA getRSA(Meta meta) {
            // 初始化对象
            // 第一个参数为加密算法，不传默认为 RSA/ECB/PKCS1Padding
            // 第二个参数为私钥（Base64字符串）
            // 第三个参数为公钥（Base64字符串）
            PrivateKey privateKey = meta.getKeyPair().getPrivate();
            PublicKey publicKey = meta.getKeyPair().getPublic();
            return new RSA(meta.getTransformation(), privateKey, publicKey);
        }

        /**
         * 编码/加密
         *
         * @param data    数据
         * @param meta    元数据（存放公钥密钥）
         * @param keyType 加密密钥类型
         * @return {@link String}
         */
        public String encode(String data, Meta meta, KeyType keyType) {
            RSA rsa = getRSA(meta);
            if (keyType == KeyType.PrivateKey) {
                return rsa.encryptBase64(data, KeyType.PrivateKey);
            }
            return rsa.encryptBase64(data, KeyType.PublicKey);
        }

        /**
         * 解码/解密
         *
         * @param encryptedData 加密数据
         * @param meta          梅塔
         * @param keyType       密钥类型
         * @return {@link String}
         */
        public String decode(String encryptedData, Meta meta, KeyType keyType) {
            RSA rsa = getRSA(meta);
            if (keyType == KeyType.PublicKey) {
                return rsa.decryptStr(encryptedData, KeyType.PublicKey);
            }
            return rsa.decryptStr(encryptedData, KeyType.PrivateKey);
        }


        /**
         * 使用公钥或私钥加密数据
         *
         * @param data 待加密的数据
         * @param key  公钥或者私有
         * @return 加密后的数据
         */
        public String encodeBySecurity(String data, Key key) throws Exception {
            Cipher cipher = Cipher.getInstance(EncType.RSA.getName());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * 使用私钥或公钥解密数据
         *
         * @param encryptedData 加密后的数据
         * @param key           私钥或者公钥
         * @return 解密后的数据
         */
        public String decodeBySecurity(String encryptedData, Key key) throws Exception {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance(EncType.RSA.getName());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        }

    }
}
