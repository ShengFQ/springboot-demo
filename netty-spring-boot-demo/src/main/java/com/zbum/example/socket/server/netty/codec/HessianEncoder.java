package com.zbum.example.socket.server.netty.codec;

import com.zbum.example.socket.server.domain.Messages;
import com.zbum.example.socket.server.utils.HessianSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 基于hessian的序列化编码器
 * */
public class HessianEncoder extends MessageToByteEncoder<Messages> {
    private HessianSerializer hessianSerializer = new HessianSerializer();
    protected void encode(ChannelHandlerContext ctx, Messages msg, ByteBuf out) throws Exception {
          byte[] bytes = hessianSerializer.serialize(msg);
          out.writeBytes(bytes);
    }
}
