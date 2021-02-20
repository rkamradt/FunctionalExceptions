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

import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author randalkamradt
 */
public class Possibly<T> {
    final T value;
    final Exception exception;
    private Possibly(T value) {
        if(value == null) throw new RuntimeException("value of Possibly cannot be null");
        this.value = value;
        this.exception = null;
    }
    private Possibly(Exception exception) {
        if(exception == null) throw new RuntimeException("exception of Possibly cannot be null");
        this.value = null;
        this.exception = exception;
    }
    public static <T> Possibly<T> of(T value ) {
        return new Possibly(value);
    }
    
    public static <T> Possibly<T> of(Exception exception ) {
        return new Possibly(exception);
    }
    public boolean is() {
        return value != null;
    }
    public Possibly<T> doIfException(Consumer<Exception> action) {
        if(exception != null) {
            action.accept(exception);
        }
        return this;
    }
    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }
    public Optional<Exception> getException() {
        return Optional.ofNullable(exception);
    }
    
}
