function plotObj(file)
    

    fid = fopen(file);
    meta = strsplit(fgets(fid), ',');
    args = cellfun(@str2num, meta(1:2));
    numberOfVar = args(1);
    numberOfObj = args(2);
    fclose(fid);
    
    
    d = csvread(file,1,0);
    
    pad = 2 + numberOfVar;
    scatter(d(:,pad+1),d(:,pad+2))