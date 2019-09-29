package no.spk.felles.remoting;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class TestResponse implements Serializable {
    private String stringEntity;
}
