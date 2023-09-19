package top.cutexingluo.tools.utils.encrypt.rsa;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/22 18:56
 */
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
}
