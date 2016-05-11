package eu.rethink.mn.policies;

import eu.rethink.mn.pipeline.message.PipeMessage;

public class PolicyEngine {
  public PolicyEngine() {}

  public static boolean authorise(PipeMessage message) {
    System.out.println("\n[Policy Engine]");
    System.out.println(message);
    System.out.println("\n");
    return true;
  }
}
