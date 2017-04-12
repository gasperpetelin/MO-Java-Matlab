classdef Binh
   properties
      NumberOfVariables = 2
      NumberOfObjectives = 2
      NumberOfConstraints = 2
      Name = 'Binh problem'
      Limits = [[0, 5];[0, 3];];

   end
   methods
      
      function obj = Binh()
      end
      
      function o = evaluate(obj, solution)
        x = solution(1);
        y = solution(2);
        o(1) = 4*x^2 + 4*y^2;
        o(2) = (x-5)^2 + (y-5)^2;
      end
      
      function [cv, numbercv] = evaluateConstraints(obj, solution)
        x = solution(1);
        y = solution(2);
        g(1) = -1 * (x-5)^2 - y^2 +25;
        g(2) = (x-8)^2 + (y+3)^2 -7.7;
        
        cns = g<0;
        cv = sum(g.*cns);
        numbercv = sum(cns);

      end
   end
end