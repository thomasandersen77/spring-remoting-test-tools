package no.spk.felles.remoting;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${mock.server.port}")
public @interface MockServerPort {
}
