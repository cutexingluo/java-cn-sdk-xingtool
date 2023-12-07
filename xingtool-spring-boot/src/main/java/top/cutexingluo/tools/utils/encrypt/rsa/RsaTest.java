package top.cutexingluo.tools.utils.encrypt.rsa;

/**
 * 测试类，不用管
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/23 19:10
 */
public class RsaTest {
    /***
     * RSA加密
     *
     */
    public String RSAEncryptDataPEM(String data, String pubKey) throws Exception {

        byte[] pubdata = RSA.encryptByPublicKey(data.getBytes("UTF-8"), pubKey);
        String outString = new String(Base64Utils.encode(pubdata));

        return outString;
    }

    /***
     * RSA解密
     *
     */
    public String RSADecryptDataPEM(String encryptData, String prvKey) throws Exception {
        byte[] encryptBytes = encryptData.getBytes();
        byte[] prvdata = RSA.decryptByPrivateKey(Base64Utils.decode(encryptData), prvKey);

        String outString = new String(prvdata, "UTF-8");
        return outString;
    }

    public void testRsa(RsaHandler rsaKeys) {
        //声明解密后的数据变量
        String decryptData = null;
        String s = "j/KLZD AW6sq2zjmF3HbVYEL LCzkhzLdN4UEIuJJZxmUjaWNQs65/f4GRpUp4PG9JqSUr9sKtbyfkZl1KbAh cRYQjfXPve8xMUzhCVxmFtx4qhOJssH LJoO3ywcpITLU9JCHXn2AtIFQ7MCpg112xeACn2gbWCmX5EdZ9GD fGaFxdGYdMre8FGUpWSUvnEsPbxh4/CYrf/v1SASExBXyQIA8jTM5M/jGeArv7KJxQFCD0n4poxGzQYFHIsZeZ2UBLnYNCH8cbBALtIY4xcUp6UFUZbwVoHoXmrsHljtz0ofb86lU6CgLOEXjMNeh3uVGi2leRbqQ WUti6TM0Q: ";
        String s2 = s.replace(' ', '+');
        try {
            decryptData = RSADecryptDataPEM(s2, rsaKeys.getServerPrvKeyPkcs8());
            System.out.println(decryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
