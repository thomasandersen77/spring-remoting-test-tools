package no.spk.felles.remoting;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestResponse implements Serializable {
    private String stringEntity;
}
