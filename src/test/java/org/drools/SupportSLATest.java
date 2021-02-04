/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.drools;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupportSLATest {
    static final Logger LOG = LoggerFactory.getLogger(SupportSLATest.class);

    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }
        
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kContainer.getKieBase()).get(DMNRuntime.class);
        DMNModel model = dmnRuntime.getModel("https://kiegroup.org/dmn/_77DB15D5-57C6-47C0-8632-DFF88CB85E4A", "support SLA");
        DMNContext context = dmnRuntime.newContext();

        context.set("Premium subscription?", false);
        context.set("Today", LocalDate.of(2021, 2, 7));

        DMNResult evaluateAll = dmnRuntime.evaluateAll(model, context);
        LOG.info("{}", evaluateAll);

        Assertions.assertThat(evaluateAll.getDecisionResultByName("Determine SLA").getResult()).isEqualTo("SLA Low");
    }
}