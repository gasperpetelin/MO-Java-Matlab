classdef KnapsackConstrainTestingNames
   properties
      varNum = 3
      objNum = 2
      BitsPerVariable = 1
      Name = 'Knapsack problem'
      Elements
      Size
   end
   methods

      function obj = KnapsackConstrainTestingNames(elements, size)
        obj.Elements = elements;
        obj.Size = size;
        obj.varNum = length(elements);
      end
      
      function o = eval(obj, solution)
        o(1) = -sum(solution'.*obj.Elements);
        o(2) = sum(solution);
        if(o(1)<-obj.Size)
            o(1) = 0;
            o(2) = 0;
        end
      end
      
      function [cv, numbercv] = consEval(obj, solution)
        cv=0;
        numbercv = 0;
        if(solution(1)==1)
            cv = -20;
            numbercv = 1;
        end
      end
      
      
   end
end