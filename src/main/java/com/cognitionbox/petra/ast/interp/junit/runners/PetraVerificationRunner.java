package com.cognitionbox.petra.ast.interp.junit.runners;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class PetraVerificationRunner extends Runner {

    private static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>());
    }

    private static final ExecutorService executorService = newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Collection<Callable<Boolean>> testClass;

    public PetraVerificationRunner(Class<?> testClass) {
        //this.testClass = PetraVerification.verify(Light.class);
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription("desc");
    }

    @Override
    public void run(RunNotifier notifier) {
        Description description = getDescription();
        notifier.fireTestRunStarted(description);

        List<Failure> failures = new ArrayList<>();
//
//        // Instantiate test class
//        Object testObject;
//        try {
//            testObject = testClass.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            notifier.fireTestFailure(new Failure(description, e));
//            notifier.fireTestRunFinished(new Result());
//            return;
//        }

        // Invoke test methods
        for (Callable<Boolean> method : testClass) {
            Description methodDescription = Description.createTestDescription("class name",method.toString(), UUID.randomUUID().toString());
            notifier.fireTestStarted(methodDescription);
            try {
                if (!method.call()){
                    throw new AssertionError();
                }
                notifier.fireTestFinished(methodDescription);
            } catch (Throwable throwable) {
                notifier.fireTestFailure(new Failure(methodDescription, throwable));
                failures.add(new Failure(methodDescription, throwable));
            }
        }

        if (failures.isEmpty()) {
            notifier.fireTestRunFinished(new Result());
        } else {
            notifier.fireTestRunFinished(new Result());
        }
    }
}

