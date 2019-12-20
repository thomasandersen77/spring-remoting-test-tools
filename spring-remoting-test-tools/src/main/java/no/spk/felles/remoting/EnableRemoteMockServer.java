package no.spk.felles.remoting;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(MockServerSpringConfiguration.class)
public @interface EnableRemoteMockServer {
}
