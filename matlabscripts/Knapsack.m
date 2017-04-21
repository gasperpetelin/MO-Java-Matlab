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