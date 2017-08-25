# serviceDownload
断点续传


 Fileinfo fileinfo = new Fileinfo(0,"xxx地址",
                     "xxx.xxx",0,0);
 Intent intent = new Intent(this, DownloadService.class);
 intent.setAction(DownloadService.ACTION_START);
 intent.putExtra("fileinfo",fileinfo);
 startService(intent);
