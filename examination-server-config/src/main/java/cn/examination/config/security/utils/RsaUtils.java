package cn.examination.config.security.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author hxy
 * @date 2020/9/26
 */
public class RsaUtils {

    private static final int DEFAULT_KEY_SIZE = 2048;

    /**从文件中读取公钥
     */
//    public static PublicKey getPublicKey(String filename) throws Exception {
//
//        byte[] bytes = readFile(filename);
//        return getPublicKey(bytes);
//    }
//
//
//    /**从文件中读取密钥
//     */
//    public static PrivateKey getPrivateKey(String filename) throws Exception {
//        byte[] bytes = readFile(filename);
//        return getPrivateKey(bytes);
//
//    }

    /**
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        bytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);

    }


    /**获取密钥
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        bytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);

    }

    /**
     * 根据密文，生成rsa公钥和私钥,并写入指定文件
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    /**读文件
     */
    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes(new File(fileName).toPath());

    }

    /**写文件
     */
    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);

    }

    private RsaUtils() {

    }
}
