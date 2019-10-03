package org.dcm4che.test.remote;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class Warp extends ParentRunner<FrameworkMethod> {
    private final ConcurrentMap<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<FrameworkMethod, Description>();

	public Warp(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected List<FrameworkMethod> getChildren() {
		return getTestClass().getAnnotatedMethods(Test.class);
	}

	@Override
    protected Description describeChild(FrameworkMethod method) {
        Description description = methodDescriptions.get(method);

        if (description == null) {
            description = Description.createTestDescription(getTestClass().getJavaClass(),
                    testName(method), method.getAnnotations());
            methodDescriptions.putIfAbsent(method, description);
        }

        return description;
    }

	@Override
	protected void runChild(FrameworkMethod child, RunNotifier notifier) {
		System.out.println("running test " + child);
		notifier.fireTestStarted(describeChild(child));
		Method method = child.getMethod();
		System.out.println(method);
		
		try {
			//Object newInstance = getTestClass().getOnlyConstructor().newInstance();
			//method.invoke(newInstance, new Object[] {});
			
	        WarpGate gate = WarpUnit.builder()
	                .primaryClass(getTestClass().getJavaClass())
	                .createGate();
	        
	        System.out.println(">>>>> " + method.getName());
	        gate.warp(method.getName(), new Object[] {});
			
		} catch (Exception e) {
			notifier.fireTestFailure(new Failure(describeChild(child), e));
		} finally {
			notifier.fireTestFinished(describeChild(child));
		}
	}
	
	protected String testName(FrameworkMethod method) {
        return method.getName();
    }

}
