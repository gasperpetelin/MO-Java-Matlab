function plotObj(file)
    d = csvread(file,1,0);
    scatter(d(:,5),d(:,6))