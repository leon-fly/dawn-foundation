package com.dawn.foundation.util;


import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2019/5/6
 */
public class ServletRequestUtil {

    public static String getRequestBody(HttpServletRequest request, String charset) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(request.getInputStream(), outputStream);
        return new String(outputStream.toByteArray(), charset);
    }
}
