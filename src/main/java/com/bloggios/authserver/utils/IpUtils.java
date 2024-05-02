package com.bloggios.authserver.utils;

import com.bloggios.authserver.constants.ServiceConstants;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.utils
 * Created_on - May 02 - 2024
 * Created_at - 12:23
 */

@UtilityClass
public class IpUtils {

    public static String getRemoteAddress(HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getHeader(ServiceConstants.X_FORWARDED_FOR);
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0].trim();
        }
        return Objects.isNull(clientIp) ? httpServletRequest.getRemoteAddr() : clientIp;
    }
}
