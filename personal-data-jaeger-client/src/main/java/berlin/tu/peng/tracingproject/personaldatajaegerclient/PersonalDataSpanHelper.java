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
import io.opentracing.tag.Tag;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link PersonalDataSpan} represents an extension of OpenTracing's Span
 * regarding the tracing of the processing of personal data.
 *
 * @author Joris Clement
 * @author Juri Welz
 */
public class PersonalDataSpanHelper {

    public static final StringTag PURPOSE = new StringTag("purpose");
    public static final StringTag DATA_CATEGORY = new StringTag("category");
    public static final StringTag RECIPIENTS = new StringTag("recipients");
    public static final BooleanTag TRANSFERRED_TO_3RDPARTY = new BooleanTag("3rdparty");
    public static final StringTag STORAGE_DURATION = new StringTag("duration");
    public static final StringTag ORIGIN = new StringTag("origin");
    public static final BooleanTag AUTOMATED = new BooleanTag("auto");

    private final Span span;

    private final List<String> recipients = new ArrayList<>();
    private final List<String> purposes = new ArrayList<>();
    private final List<String> dataCategories = new ArrayList<>();
    private final List<String> origins = new ArrayList<>();


    public PersonalDataSpanHelper(Span span) {
        this.span = span;
    }

    public static void setPurposes(Span span, List<String> purposes) {
        PURPOSE.set(span, String.join(",", purposes));
    }

    public static void setRecipients(Span span, List<String> recipients) {
        RECIPIENTS.set(span, String.join(", ", recipients));
    }

    public static void setDataCategories(Span span, List<String> dataCategories) {
        DATA_CATEGORY.set(span, String.join(",", dataCategories));
    }

    public static void setOrigins(Span span, List<String> origins) {
        ORIGIN.set(span, String.join(",", origins));
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

    public PersonalDataSpanHelper setPurposes(List<String> purposes) {
        setPurposes(this.span, purposes);
        return this;
    }

    public PersonalDataSpanHelper setDataCategories(List<String> dataCategories) {
        setDataCategories(this.span, dataCategories);
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

    public PersonalDataSpanHelper setOrigins(List<String> origins) {
        setOrigins(this.span, origins);
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
        this.setPurposes(purposes);
        this.setDataCategories(dataCategories);

        recipients.forEach(this::addRecipient);
        collectRecipients();

        this.setTransferredTo3rdParty(transferredTo3rdParty);
        this.setStorageDuration(storageDuration);
        this.setOrigins(origins);
        this.setAutomated(automated);
    }

    public PersonalDataSpanHelper addRecipient(String recipient){
        recipients.add(recipient);
        return this;
    }

    public PersonalDataSpanHelper collectRecipients(){
        setPurposes(this.span, this.purposes);
        return this;
    }

    public PersonalDataSpanHelper addPurpose(String purpose){
        purposes.add(purpose);
        return this;
    }

    public PersonalDataSpanHelper collectPurposes(){

        setPurposes(this.span, this.recipients);
        return this;
    }

    public PersonalDataSpanHelper addCategory(String dataCategory){
        dataCategories.add(dataCategory);
        return this;
    }

    public PersonalDataSpanHelper collectDataCategories(){

        setDataCategories(this.span, this.dataCategories);
        return this;
    }

    public PersonalDataSpanHelper addOrigin(String origin){
        origins.add(origin);
        return this;
    }

    public PersonalDataSpanHelper collectOrigins(){
        setOrigins(this.span, this.origins);
        return this;
    }

    public PersonalDataSpanHelper collectAllLists(){
        collectPurposes();
        collectDataCategories();
        collectRecipients();
        collectOrigins();
        return this;
    }

}
