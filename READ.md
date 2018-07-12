    
### 调用方法
    
            DocAndExlParams docAndExlParams = new DocAndExlParams();
            docAndExlParams.setPath("/Users/lujunling/Desktop/electronic-contract-open-api/open-api-service/doc-convert/tmp/doc/"+path);
            docAndExlParams.setType(TypeEnum.DOCTOPDF);
            docAndExlParams.setFileType(DocEnum.DOC);
            docAndExlParams.setUserid(1l);
            docAndExlParams.setUsercontractid(1l);
            switch (type) {
                case "pdf":
                    docAndExlParams.setFileType(DocEnum.PDF);
                    break;
                case "doc":
                    docAndExlParams.setFileType(DocEnum.DOC);
                    break;
                case "xls":
                    docAndExlParams.setFileType(DocEnum.XLS);
                    break;
    
            }
    
                amqpTemplate.convertAndSend("doc-converter-queue", JSON.toJSONString(docAndExlParams));
                
               
               