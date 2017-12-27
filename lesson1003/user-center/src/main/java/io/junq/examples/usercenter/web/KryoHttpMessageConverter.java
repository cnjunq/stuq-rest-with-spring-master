package io.junq.examples.usercenter.web;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import io.junq.examples.usercenter.persistence.model.Principal;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.web.dto.UserDto;

public class KryoHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	
	public static final MediaType KRYO = new MediaType("application", "x-kryo");
	
    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return createKryo();
        }
    };
	
    public KryoHttpMessageConverter() {
        super(KRYO);
    }

    private static final Kryo createKryo(){
        final Kryo kryo = new Kryo();
        kryo.register(UserDto.class, 1);
        kryo.register(Role.class, 2);
        kryo.register(Privilege.class, 3);
        kryo.register(Principal.class, 4);

        return kryo;
    }

	@Override
	protected boolean supports(Class clazz) {
		return Object.class.isAssignableFrom(clazz);
	}

	@Override
	protected Object readInternal(Class clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		final Input input = new Input(inputMessage.getBody());
        return kryoThreadLocal.get().readClassAndObject(input);
	}

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		final Output output = new Output(outputMessage.getBody());
        kryoThreadLocal.get().writeClassAndObject(output, object);
        output.flush();
	}
}
