package top.cutexingluo.tools.utils.encrypt.rsa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import top.cutexingluo.tools.utils.encrypt.all.XTEncryptUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA 公钥私钥接口
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/22 18:56
 */
@ConditionalOnClass(KeyPair.class)
public abstract class RsaHandler {
    protected String serverPubKey;
    protected String serverPrvKeyPkcs8;

    public String getServerPubKey() {
        return saveServerPubKey();
    }

    public String getServerPrvKeyPkcs8() {
        return saveServerPrvKeyPkcs8();
    }

    /**
     * @return ServerPubKey
     */
    protected abstract String saveServerPubKey();

    /**
     * @return ServerPrvKeyPkcs8
     */
    protected abstract String saveServerPrvKeyPkcs8();

    public KeyPair getKeyPair() {
        XTEncryptUtil.X_RSA rsa = XTEncryptUtil.X_RSA.newInstance();
        PublicKey pubK = rsa.getPublicKeyByBase64(getServerPubKey());
        PrivateKey prvK = rsa.getPrivateKeyByBase64(getServerPrvKeyPkcs8());
        return new KeyPair(pubK, prvK);
    }
}
