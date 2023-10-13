package de.pdbm.mpft;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

class SimpleFallbackHandler implements FallbackHandler<String> {

    @Override
    @Log
    public String handle(ExecutionContext executionContext) {
        return "from 'SimpleFallbackHandler.handle()'";
    }

}
