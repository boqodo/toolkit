package java.zookeeper.seria;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.nustaq.serialization.FSTConfiguration;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class JRedisSerializationUtils {

	public JRedisSerializationUtils() {
	}

	static FSTConfiguration configuration = FSTConfiguration
	//.createDefaultConfiguration();
			.createStructConfiguration();

	public static byte[] serialize(Object obj) {
		return configuration.asByteArray(obj);
	}

	public static Object unserialize(byte[] sec) {
		return configuration.asObject(sec);
	}

	public static byte[] kryoSerizlize(Object obj) {
		Kryo kryo = new Kryo();
		byte[] buffer = new byte[2048];
		try{
			Output output = new Output(buffer);
			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		} catch (Exception e) {
		}
		return buffer;
	}

	static Kryo kryo = new Kryo();

	public static Object kryoUnSerizlize(byte[] src) {
		try{
			Input input = new Input(src);
			return kryo.readClassAndObject(input);
		} catch (Exception e) {
		}
		return kryo;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] protostuffSerialize(Object source){
		Class clazz = (Class) source.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE );
        Schema schema = SchemaUtils.getSchema( clazz );
        return ProtostuffIOUtil.toByteArray( source, schema, buffer );
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T protostuffDeserialize( byte[] bytes ,  T result){
		Schema schema = SchemaUtils.getSchema( result.getClass() );
		ProtostuffIOUtil.mergeFrom( bytes, result, schema );
        return result;
	}

	// jdk原生序列换方案
	public static byte[] jdkserialize(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object jdkdeserialize(byte[] bits) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bits);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
