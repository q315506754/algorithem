package com.jiangli.ftp.custom;

import org.apache.ftpserver.listener.nio.FtpResponseEncoder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;

public class CustomFtpServerProtocolCodecFactory implements ProtocolCodecFactory {
    //private final ProtocolDecoder decoder = new TextLineDecoder(Charset
    //        .forName("UTF-8"));
    private final ProtocolDecoder decoder = new CustomTextLineDecoder(Charset
            .forName("UTF-8"));

    private final ProtocolEncoder encoder = new FtpResponseEncoder();

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }
}