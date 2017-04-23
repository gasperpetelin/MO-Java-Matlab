# jMetal problem evaluation in Matlab

### ZagonTest
Primer klica datoteke ZagonTest

```java
/*
Konfiguracijske datoteka iz katere preberemo imena polj v Matlab razredu.
DoubleSolutionMatlabManagerConfig deduje iz abstraktnega razreda MatlabManagerConfig 
v katerem so shranjene osnovne nastavitve problema
*/
DoubleSolutionMatlabManagerConfig conf = new DoubleSolutionMatlabManagerConfig();

```
neki
```java
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


```Matlab
classdef Knapsack
   properties
      NumberOfVariables = 3
      NumberOfObjectives = 2
      BitsPerVariable = 1
      Name = 'Knapsack problem'
      Elements
      Size
   end
   methods

      function obj = Knapsack(elements, size)
        obj.Elements = elements;
        obj.Size = size;
        obj.NumberOfVariables = length(elements);
      end
      
      function o = evaluate(obj, solution)
        %solution = solution(:, 1);
        o(1) = -sum(solution'.*obj.Elements);
        o(2) = sum(solution);
        if(o(1)<-obj.Size)
            o(1) = 0;
            o(2) = 0;
        end
      end
   end
end
```
