package express.utils;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Utils {

  /**
   * Write all data from an InputStream in an String
   *
   * @param is The source inputstream
   * @return The data as string
   */
  public static String streamToString(InputStream is) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;

      while ((line = br.readLine()) != null)
        sb.append(line);

      return sb.toString();
    } catch (IOException e) {
      // TODO: Handle error
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns the MIME-Type of an file.
   *
   * @param file The file.
   * @return The MIME-Type.
   */
  public static String getContentType(File file) {
    String ex = getExtension(file);
    String contentType = MediaType.getForExtension(ex).getMIME();

    if (contentType == null)
      contentType = "application/octet-stream";

    return contentType;
  }

  /**
   * Generates an random token with SecureRandom
   *
   * @param byteLength The token length
   * @param radix      The base
   * @return An token with the base of radix
   */
  public static String randomToken(int byteLength, int radix) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] token = new byte[byteLength];
    secureRandom.nextBytes(token);
    return new BigInteger(1, token).toString(radix); //hex encoding
  }

  /**
   * @return Your ip.
   */
  public static String getYourIp() throws UnknownHostException {
    return Inet4Address.getLocalHost().getHostAddress();
  }

  /**
   * Extract the extension from the file.
   *
   * @param file The file.
   * @return The extension.
   */
  public static String getExtension(@NotNull File file) {
    char[] buffer = new char[255];
    int bufferIndex = 0;

    for (char c : file.getAbsolutePath().toCharArray()) {
      if (c == '\\' || c == '.' || c == '/')
        bufferIndex = 0;
      else
        buffer[bufferIndex++] = c;
    }
    return new String(Arrays.copyOf(buffer, bufferIndex));
  }

}
