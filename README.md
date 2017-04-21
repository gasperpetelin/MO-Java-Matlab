# jMetal problem evaluation in Matlab

Primer klica datoteke ZagonTest

```java
DoubleSolutionMatlabManagerConfig conf = new DoubleSolutionMatlabManagerConfig();
DoubleMatlabManager manager = new DoubleMatlabManager(conf);
manager.openSession();

manager.setPath("matlabscripts");
DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "ZagonTest");
ExternalDoubleProblem p = builder.build();

Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<DoubleSolution>(p,
        new SBXCrossover(0.9, 20),
        new PolynomialMutation(2, 20))
        .build() ;
AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

System.out.println(algorithm.getResult());
System.out.println(algorithmRunner.getComputingTime());

manager.closeSession();
```

Datoteka ZagonTest

```matlab
classdef ZagonTest
   properties
      NumberOfVariables = 6
      NumberOfObjectives = 10
      Name = 'Zagon'
      Limits=[[-100,100];[-100,100];[-100,100];[-100,100];[-100,100];[-100,100]];
   end
   methods

      function obj = ZagonTest()
      end
      
      function o = evaluate(obj, solution)
        global vhod;
        vhod = solution;
        o = zagon(vhod);
      end
   end
end
```
