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

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.impl.DMNRuntimeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanTest {
    static final Logger LOG = LoggerFactory.getLogger(LoanTest.class);

    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }
        
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kContainer.getKieBase()).get(DMNRuntime.class);
        DMNModel model = dmnRuntime.getModel("https://kiegroup.org/dmn/_79B69A7F-5A25-4B53-BD6A-3216EDC246ED", "loan");
        DMNContext context = dmnRuntime.newContext();
        context.set("Credit score", 680);
        context.set("Salary", 100_000);
        context.set("Loan", Map.of("amount", 280_000, "years", 10));

        DMNResult evaluateAll = dmnRuntime.evaluateAll(model, context);
        LOG.info("{}", evaluateAll);
    }

    @Test
    public void test2() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }
        
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kContainer.getKieBase()).get(DMNRuntime.class);
        DMNModel model = dmnRuntime.getModel("https://kiegroup.org/dmn/_79B69A7F-5A25-4B53-BD6A-3216EDC246ED3", "loan2");
        DMNContext context = dmnRuntime.newContext();
        context.set("Credit score", 680);
        context.set("Salary", 100_000);
        context.set("Loan", Map.of("amount", 280_000, "years", 10));

        DMNResult evaluateAll = dmnRuntime.evaluateAll(model, context);
        LOG.info("{}", evaluateAll);
    }
}