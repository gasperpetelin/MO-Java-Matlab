classdef Schaffer
   properties
      NumberOfVariables = 1
      NumberOfObjectives = 2
      Name = 'Schaffer problem'
      %Limits = [[-100, 100];[-100, 100];[-100, 100]];
      Limits = [[-1,1];];
   end
   methods
      
      function obj = Schaffer()
      end
      
      function o = evaluate(obj, solution)
        o(1) = solution(1)^2;
        o(2) = (solution(1)-2)^2;
      end

   end
end