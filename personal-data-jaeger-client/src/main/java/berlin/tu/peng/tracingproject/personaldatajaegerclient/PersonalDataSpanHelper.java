/*
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

import java.util.List;


/**
 * {@link PersonalDataSpan} represents an extension of OpenTracing's Span
 * regarding the tracing of the processing of personal data.
 * <p> Please note: the static methods and the non-static methods should not be mixed
 *
 * @author Joris Clement
 * @author Juri Welz
 */
public class PersonalDataSpanHelper {

    private Integer recipientCount = 0;
    private Integer purposeCount = 0;
    private Integer dataCategoryCount = 0;
    private Integer originCount = 0;

    public static final String PURPOSE_KEY = "purpose";
    public static final String DATA_CATEGORY_KEY = "category";
    public static final String RECIPIENTS_KEY = "recipients";
    public static final String ORIGIN_KEY = "origin";

    public static final BooleanTag TRANSFERRED_TO_3RDPARTY = new BooleanTag("3rdparty");
    public static final StringTag STORAGE_DURATION = new StringTag("duration");
    public static final BooleanTag AUTOMATED = new BooleanTag("auto");

    private final Span span;

    public PersonalDataSpanHelper(Span span) {
        this.span = span;
    }

    public static void setPurposes(Span span, List<String> purposes) {
        int staticCount = 0;
        for (String purpose : purposes) {
            setCountedStringTag(span, PURPOSE_KEY, staticCount++, purpose);
        }
    }

    public static void setRecipients(Span span, List<String> recipients) {
        int staticCount = 0;
        for (String recipient : recipients) {
            setCountedStringTag(span, RECIPIENTS_KEY, staticCount++, recipient);
        }
    }

    public static void setDataCategories(Span span, List<String> dataCategories) {
        int staticCount = 0;
        for (String dataCategory : dataCategories) {
            setCountedStringTag(span, DATA_CATEGORY_KEY, staticCount++, dataCategory);
        }
    }

    public static void setOrigins(Span span, List<String> origins) {
        int staticCount = 0;
        for (String origin : origins) {
            setCountedStringTag(span, ORIGIN_KEY, staticCount++, origin);
        }
    }

    public static void setTransferredTo3rdParty(Span span,
                                                boolean transferredTo3rdParty) {
        TRANSFERRED_TO_3RDPARTY.set(span, transferredTo3rdParty);
    }

    public static void setStorageDuration(Span span, String storageDuration) {
        STORAGE_DURATION.set(span, storageDuration);
    }


    public static void setAutomated(Span span, boolean automated) {
        AUTOMATED.set(span, automated);
    }

    public static void setPersonalInfo(Span span,
                                       List<String> purposes,
                                       List<String> dataCategories,
                                       List<String> recipients,
                                       boolean transferredTo3rdParty,
                                       String storageDuration,
                                       List<String> origins,
                                       boolean automated) {
        setPurposes(span, purposes);
        setDataCategories(span, dataCategories);
        setRecipients(span, recipients);
        setTransferredTo3rdParty(span, transferredTo3rdParty);
        setStorageDuration(span, storageDuration);
        setOrigins(span, origins);
        setAutomated(span, automated);
    }


    public PersonalDataSpanHelper addPurposes(List<String> purposes) {
        purposes.forEach(this::addPurpose);
        return this;
    }

    public PersonalDataSpanHelper addDataCategories(List<String> dataCategories) {
        dataCategories.forEach(this::addDataCategory);
        return this;
    }

    public PersonalDataSpanHelper addRecipients(List<String> recipients) {
        recipients.forEach(this::addRecipient);
        return this;
    }

    public PersonalDataSpanHelper setTransferredTo3rdParty(
            boolean transferredTo3rdParty) {
        setTransferredTo3rdParty(this.span, transferredTo3rdParty);
        return this;
    }

    public PersonalDataSpanHelper setStorageDuration(String storageDuration) {
        setStorageDuration(this.span, storageDuration);
        return this;
    }

    public PersonalDataSpanHelper addOrigins(List<String> origins) {
        origins.forEach(this::addOrigin);
        return this;
    }

    public PersonalDataSpanHelper setAutomated(boolean automated) {
        setAutomated(this.span, automated);
        return this;
    }

    public void setPersonalInfo(List<String> purposes,
                                List<String> dataCategories,
                                List<String> recipients,
                                boolean transferredTo3rdParty,
                                String storageDuration,
                                List<String> origins,
                                boolean automated) {
        this.addPurposes(purposes);
        this.addDataCategories(dataCategories);
        this.addRecipients(recipients);
        this.setTransferredTo3rdParty(transferredTo3rdParty);
        this.setStorageDuration(storageDuration);
        this.addOrigins(origins);
        this.setAutomated(automated);
    }

    public PersonalDataSpanHelper addRecipient(String recipient) {
        setCountedStringTag(this.span, RECIPIENTS_KEY, recipientCount++, recipient);
        return this;
    }

    public PersonalDataSpanHelper addPurpose(String purpose) {
        setCountedStringTag(this.span, PURPOSE_KEY, purposeCount++, purpose);
        return this;
    }

    public PersonalDataSpanHelper addDataCategory(String dataCategory) {
        setCountedStringTag(this.span, DATA_CATEGORY_KEY, dataCategoryCount++, dataCategory);
        return this;
    }

    public PersonalDataSpanHelper addOrigin(String origin) {
        setCountedStringTag(this.span, ORIGIN_KEY, originCount++, origin);
        return this;
    }

    public static void setCountedStringTag(Span span, String key, Integer count, String value) {
        final StringTag newTag = new StringTag(key + "_" + count.toString());
        newTag.set(span, value);
    }

    public void finishSpan() {
        span.finish();
    }

}
