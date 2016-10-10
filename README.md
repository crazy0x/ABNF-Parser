# ABNF-Parser
科大讯飞在线语义本地化

科大讯飞的私有语义是需要在其开放平台进行配置的，无法配合其离线语法脱机使用，为了解决这个问题，做了简单的解析工作，返回格式和讯飞的在线语义尽量保持一致，主要自用。

讯飞平台的语法参考资料：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=7595

```Java
ABNFProcessor.INSTANCE.init(ABNFfolderpath);
JSONObject result = ABNFParser.parse4ABNF(grammarResult);

ABNFfolderpath——ABNF文件的文件夹绝对路径
grammarResult——讯飞离线语法的识别结果
```
