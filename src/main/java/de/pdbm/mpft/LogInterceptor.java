package de.pdbm.mpft;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Logger;

import static jakarta.interceptor.Interceptor.Priority.*;

@Log
@Interceptor
@Priority(PLATFORM_AFTER + 11)
//@Priority(Integer.MAX_VALUE)
//@Priority(APPLICATION)
//@Priority(PLATFORM_BEFORE)
public class LogInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LogInterceptor.class.getCanonicalName());

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        String tmp = context.getTarget().getClass().getSimpleName(); // with proxy part
        String simpleName = tmp.substring(0, tmp.indexOf("$"));
        LOGGER.info(simpleName + "." + context.getMethod().getName() + "() called");
        Object result = context.proceed();
        LOGGER.info(simpleName + "." + context.getMethod().getName() + "() completed");
        return result;
    }
}
