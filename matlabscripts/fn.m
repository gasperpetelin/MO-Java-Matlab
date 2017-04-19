function m = fn()
    ysize = 100;
    xsize = 100;
    
    m  = zeros(ysize, xsize);
    n = [[0 1 0];[1 10 1];[0 1 0]];
    for i=0:10000
        m = conv2(m, n, 'same')/sum(sum(n));
        %m(1, :) = linspace(0, 3, xsize);
        %m(end, :) = linspace(3, 0, xsize);
        m(35:45,25:40) = 1; 
    end
end
