package com.darkneees.soapfrontservice.log;

import ch.qos.logback.core.OutputStreamAppender;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StaticOutputStreamAppender<E> extends OutputStreamAppender<E> {

    private static final DelegatingOutputStream DELEGATING_OUTPUT_STREAM = new DelegatingOutputStream(null);

    @Override
    public void start() {
        setOutputStream(DELEGATING_OUTPUT_STREAM);
        super.start();
    }

    public static void setDelegatingOutputStream(OutputStream outputStream) {
        DELEGATING_OUTPUT_STREAM.setOutputStream(outputStream);
    }

    private static class DelegatingOutputStream extends FilterOutputStream {
        public DelegatingOutputStream(OutputStream out) {
            super(new OutputStream() {
                @Override
                public void write(int b) throws IOException {}
            });
        }

        void setOutputStream(OutputStream outputStream) {
            this.out = outputStream;
        }
    }

}
