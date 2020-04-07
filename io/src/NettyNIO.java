import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

/**
 * @ClassName NettyOIO
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-31 14:53
 * @Version V1.0
 **/
public class NettyNIO {

    public static void main(String[] args) {
        //Configure the server
        //创建两个EventLoopGroup对象
        //创建boss线程组 用于服务端接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 创建 worker 线程组 用于进行 SocketChannel 的数据读写
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try {
            // 创建 ServerBootstrap 对象
            ServerBootstrap b = new ServerBootstrap();
            //设置使用的EventLoopGroup
            b.group(bossGroup, workerGroup)
                    //设置要被实例化的为 NioServerSocketChannel 类
                    .channel(NioServerSocketChannel.class)
                    // 设置 NioServerSocketChannel 的处理器
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 设置连入服务端的 Client 的 SocketChannel 的处理器
                    .childHandler(new ServerInitializer());
            // 绑定端口，并同步等待成功，即启动服务端
            ChannelFuture f = b.bind(8080);
            // 监听服务端关闭，并阻塞等待
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭两个 EventLoopGroup 对象
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
