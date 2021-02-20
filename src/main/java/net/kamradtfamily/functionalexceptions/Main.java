/*
 * The MIT License
 *
 * Copyright 2021 randalkamradt.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.kamradtfamily.functionalexceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author randalkamradt
 */
public class Main {
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String [] argv) {
        System.out.println("/good.json = " + parseFileAndPrettyPrint("/good.json"));
        System.out.println("/bad.json = " + parseFileAndPrettyPrint("/bad.json"));
        
        var nis =
            Main.class.getResourceAsStream("/good.json");
        Stream.generate(PossiblySupplier.of(() -> nis.read()))
            .takeWhile(ch -> ch.getValue().orElse(-1) != -1)
            .filter(ch -> ch.is())
            .map(ch -> (char)(int)ch.getValue().get())
            .forEach(ch -> System.out.print(ch));
    }
    
    public static String parseFileAndPrettyPrint(String streamName) {
        var r = new BufferedReader(
            new InputStreamReader(
                    Main.class.getResourceAsStream(streamName)));
        return Optional.of(r.lines()
            .collect(StringBuilder::new, 
                    StringBuilder::append,
                    StringBuilder::append))
            .map(StringBuilder::toString)
            .map(PossiblyFunction.of(s -> objectMapper.readTree(s)))
            .orElse(Possibly.of(new Exception("no value exists")))
            .doIfException(e -> e.printStackTrace(System.out))
            .getValue()
            .map(n -> n.toPrettyString())
            .orElse("unparsable");
        
    }
}
