/*
 * Copyright (C) 2012-2013 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package java.thrift;

import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thrift.tutorial.Calculator;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.NettyServerConfigBuilder;
import com.facebook.nifty.core.NiftyBootstrap;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.facebook.nifty.guice.NiftyModule;
import com.google.inject.Guice;
import com.google.inject.Stage;

/**
 * An example of how to create a Nifty server without plugging into config or lifecycle framework.
 */
public class JavaNettyServer {
	private static final Logger log = LoggerFactory.getLogger(JavaNettyServer.class);

	private static final NiftyModule nm = new NiftyModule() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void configureNifty() {
			CalculatorHandler handler= new CalculatorHandler();
			Calculator.Processor processor = new Calculator.Processor(handler);
			ThriftServerDef tsdb = new ThriftServerDefBuilder().listen(9090).withProcessor(processor).build();

			bind().toInstance(tsdb);
			withNettyServerConfig(NettyConfigProvider.class);
		}
	};

	public static void main(String[] args) throws Exception {
		final NiftyBootstrap bootstrap = Guice.createInjector(Stage.PRODUCTION, nm)
				.getInstance(NiftyBootstrap.class);

		bootstrap.start();
		System.out.println("Starting the simple server...");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				bootstrap.stop();
			}
		});
	}

	public static class NettyConfigProvider implements Provider<NettyServerConfig> {
		@Override
		public NettyServerConfig get() {
			NettyServerConfigBuilder nettyConfigBuilder = new NettyServerConfigBuilder();
			nettyConfigBuilder.getSocketChannelConfig().setTcpNoDelay(true);
			nettyConfigBuilder.getSocketChannelConfig().setConnectTimeoutMillis(5000);
			nettyConfigBuilder.getSocketChannelConfig().setTcpNoDelay(true);
			return nettyConfigBuilder.build();
		}
	}
}
