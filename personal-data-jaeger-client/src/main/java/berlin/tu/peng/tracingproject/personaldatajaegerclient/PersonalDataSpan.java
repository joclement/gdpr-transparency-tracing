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

import java.util.List;


/**
 * {@link PersonalDataSpan} represents an extension of OpenTracing's Span
 * regarding the tracing of the processing of personal data.
 *
 * @author Joris Clement
 */
public interface PersonalDataSpan extends Span {

    public static final StringTag PURPOSE = new StringTag("purpose");

    public static final StringTag DATA_CATEGORY = new StringTag("category");

    public static final StringTag RECIPIENTS = new StringTag("recipients");

    public static final BooleanTag TRANSFERRED_TO_3RDPARTY = new BooleanTag("3rdparty");

    public static final StringTag STORAGE_DURATION = new StringTag("duration");

    public static final StringTag ORIGIN = new StringTag("origin");

    public static final BooleanTag AUTOMATED = new BooleanTag("auto");


    default Span setPurpose(String purpose) {
        setTag(PURPOSE, purpose);
        return this;
    }

    default Span setRecipients(List<String> recipients) {
        setTag(RECIPIENTS, recipients.toString());
        return this;
    }

    default Span setDataCategory(String dataCategory) {
        setTag(DATA_CATEGORY, dataCategory);
        return this;
    }

    default Span setTransferredTo3rdParty(boolean transferredTo3rdParty) {
        setTag(TRANSFERRED_TO_3RDPARTY, transferredTo3rdParty);
        return this;
    }

    default Span setStorageDuration(String storageDuration) {
        setTag(STORAGE_DURATION, storageDuration);
        return this;
    }

    default Span setOrigin(String origin) {
        setTag(ORIGIN, origin);
        return this;
    }

    default Span setAutomated(boolean automated) {
        setTag(AUTOMATED, automated);
        return this;
    }

    default Span setPersonalInfo(String purpose,
                                 String dataCategory,
                                 List<String> recipients,
                                 boolean transferredTo3rdParty,
                                 String storageDuration,
                                 String origin,
                                 boolean automated) {
        setPurpose(purpose);
        setDataCategory(dataCategory);
        setRecipients(recipients);
        setTransferredTo3rdParty(transferredTo3rdParty);
        setStorageDuration(storageDuration);
        setOrigin(origin);
        setAutomated(automated);
        return this;
    }
}
