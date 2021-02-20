package cn.examination.config.configuration;

import cn.examination.config.security.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 解析Rsa公/私匙配置类
 * @author hxy
 * @date 2020/9/26
 */
@ConfigurationProperties(prefix = "sso.key")
public class AuthRsaKeyProperties {

    private String publicKeyPath;
    private String privateKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;


    /**加载文件当中的公钥、私钥
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
     * 并且只会被服务器执行一次。PostConstruct在构造函数之后执行，
     */
    @PostConstruct
    public void loadKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicKeyPath);
        privateKey = RsaUtils.getPrivateKey(privateKeyPath);

    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        this.publicKeyPath = path + publicKeyPath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        this.privateKeyPath = path + privateKeyPath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public AuthRsaKeyProperties() {
    }
}
