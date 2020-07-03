/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package berlin.tu.peng.tracingproject.personaldatajaegerclient;

import io.opentracing.Span;
import io.opentracing.tag.StringTag;
import io.opentracing.tag.BooleanTag;

import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerSpanContext;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.Reference;

import java.util.List;
import java.util.Map;

/**
 * Class to extend the jaeger client to trace the processing of personal data.
 *
 * @author Joris Clement
 */
public class PersonalDataSpan extends JaegerSpan {

    public static final StringTag PURPOSE = new StringTag("purpose");

    public static final StringTag DATA_CATEGORY = new StringTag("category");

    public static final StringTag RECIPIENTS = new StringTag("recipients");

    public static final BooleanTag TRANSFERRED_TO_3RDPARTY = new BooleanTag("3rdparty");

    public static final StringTag STORAGE_DURATION = new StringTag("duration");

    public static final StringTag ORIGIN = new StringTag("origin");

    public static final BooleanTag AUTOMATED = new BooleanTag("auto");


    protected PersonalDataSpan(
        JaegerTracer tracer,
        String operationName,
        JaegerSpanContext context,
        long startTimeMicroseconds,
        long startTimeNanoTicks,
        boolean computeDurationViaNanoTicks,
        Map<String, Object> tags,
        List<Reference> references) {
        super(tracer,
              operationName,
              context,
              startTimeMicroseconds,
              startTimeNanoTicks,
              computeDurationViaNanoTicks,
              tags,
              references);
    }

    public Span setPurpose(String purpose) {
        this.setTag(PURPOSE, purpose);
        return this;
    }

    public Span setRecipients(List<String> recipients) {
        this.setTag(RECIPIENTS, recipients.toString());
        return this;
    }

    public Span setDataCategory(String dataCategory) {
        this.setTag(DATA_CATEGORY, dataCategory);
        return this;
    }

    public Span setTransferredTo3rdParty(boolean transferredTo3rdParty) {
        this.setTag(TRANSFERRED_TO_3RDPARTY, transferredTo3rdParty);
        return this;
    }

    public Span setStorageDuration(String storageDuration) {
        this.setTag(STORAGE_DURATION, storageDuration);
        return this;
    }

    public Span setOrigin(String origin) {
        this.setTag(ORIGIN, origin);
        return this;
    }

    public Span setAutomated(boolean automated) {
        this.setTag(AUTOMATED, automated);
        return this;
    }

    public void setPersonalInfo(String purpose,
                                String dataCategory,
                                List<String> recipients,
                                boolean transferredTo3rdParty,
                                String storageDuration,
                                String origin,
                                boolean automated) {
        this.setPurpose(purpose);
        this.setDataCategory(dataCategory);
        this.setRecipients(recipients);
        this.setTransferredTo3rdParty(transferredTo3rdParty);
        this.setStorageDuration(storageDuration);
        this.setOrigin(origin);
        this.setAutomated(automated);
    }
}
