package fitnesse.testrunner.run;

import fitnesse.FitNesseContext;
import fitnesse.wiki.WikiPage;

import java.util.ArrayList;
import java.util.List;

public class TestRunFactoryRegistry {
  public final static TestRunFactory DEFAULT =
    new TestRunFactory() {
      @Override
      public boolean canRun(List<WikiPage> pages) {
        return true;
      }

      @Override
      public TestRun createRun(List<WikiPage> pages) {
        return new PerTestSystemTestRun(pages);
      }
    };

  private final List<TestRunFactory> testRunFactories = new ArrayList<>();
  private final FitNesseContext context;

  public TestRunFactoryRegistry(FitNesseContext context) {
    this.context = context;
  }

  public void addFactory(TestRunFactory factory) {
    testRunFactories.add(0, factory);
  }

  public TestRun createRun(List<WikiPage> pages) {
    return getFactory(pages).createRun(pages);
  }

  public TestRunFactory getFactory(List<WikiPage> pages) {
    return testRunFactories.stream()
      .filter(f -> f.canRun(pages))
      .findFirst()
      .orElse(DEFAULT);
  }

  public FitNesseContext getContext() {
    return context;
  }
}
