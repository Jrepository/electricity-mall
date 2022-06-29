<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <style>
        body {
            font-family: SimSun;
        }

        table.gridtable, body {
            font-size: 13px;
            color: #333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
        }

        table.gridtable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #dedede;
        }

        table.gridtable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
            text-align: center;
        }
    </style>

</head>
<body>
<div id="project">
    <div>
        <div class="ivu-card-body">
            <div>
                <div style="text-align: center;"><h3>${title}</h3></div>
                <p>
                        <span>
                            编号：${num}
                        </span>
                </p>
                <p>
                        <span>
                            时间：${createTime} - ${updateTime}（耗时：${timeDiff}秒）
                        </span>
                </p>
                <p>
                        <span>
                           执行人：${userName}
                        </span>
                </p>
                </span>
                <h3>结果：</h3>
                <table border="1" cellspacing="0" cellpadding="0" style="margin-top: 10px;" class="gridtable">
                    <tbody>
                    <tr>
                        <th style="width: 100px;" rowspan="2">名称</th>
                        <th style="width: 100px;" rowspan="2">总分</th>
                        <th style="width: 100px;" colspan="3">语数外</th>
                        <#if (flag?boolean)>
                            <th style="width: 140px;" colspan="3">理综</th>
                        <#else >
                            <th style="width: 140px;" colspan="3">文综</th>
                        </#if>
                    </tr>
                    <tr>
                        <th style="width: 100px;">语文</th>
                        <th style="width: 100px;">数学</th>
                        <th style="width: 100px;">英语</th>
                        <#if (flag?boolean)>
                            <th style="width: 100px;">物理</th>
                            <th style="width: 100px;">化学</th>
                            <th style="width: 100px;">生物</th>
                        <#else >
                            <th style="width: 100px;">政治</th>
                            <th style="width: 100px;">历史</th>
                            <th style="width: 100px;">地理</th>
                        </#if>
                    </tr>
                    <tr class="red">
                        <td>总分</td>
                        <td>-</td>
                        <#if dataList?exists && dataList?size gt 0 >
                            <td>
                                <#assign sum  = 0 >
                                <#list dataList as oneData>
                                    <#assign sum =oneData.languageScore+sum>
                                </#list>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <td>
                                <#assign sum  = 0 >
                                <#list dataList as oneData>
                                    <#assign sum =oneData.mathScore+sum>
                                </#list>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <td>
                                <#assign sum  = 0 >
                                <#list dataList as oneData>
                                    <#assign sum =oneData.englishScore+sum>
                                </#list>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <#if (flag?boolean)>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.physicsScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.chemicalScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.biologyScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                            <#else >
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.politicsScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.historyScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.geographyScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                            </#if>
                        </#if>
                    </tr>

                    <tr class="red">
                        <td>平均分</td>
                        <td>-</td>
                        <#if dataList?exists && dataList?size gt 0 >
                            <td>
                                <#assign sum  = 0 >
                                <#list dataList as oneData>
                                    <#assign sum =oneData.languageScore+sum>
                                </#list>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <td>
                                <#assign sum  = 0 >
                                <#list dataList as oneData>
                                    <#assign sum =oneData.mathScore+sum>
                                </#list>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <td>
                                <#assign sum  = 0 >
                                <#list dataList as oneData>
                                    <#assign sum =oneData.englishScore+sum>
                                </#list>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <#if (flag?boolean)>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.physicsScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.chemicalScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.biologyScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                            <#else >
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.politicsScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.historyScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                                <td> <#assign sum  = 0 >
                                    <#list dataList as oneData>
                                        <#assign sum =oneData.geographyScore+sum>
                                    </#list>
                                    <#if sum?exists>
                                        ${(sum)!}
                                    </#if>
                                </td>
                            </#if>
                        </#if>
                    </tr>
                    <#--<#list dataList as oneData>-->
                    <#list dataList?sort_by("name") as oneData>
                    <#--<#list dataList?sort_by("name")?reverse as oneData>-->
                        <tr>
                            <td>${(oneData.name)!"-"}</td>
                            <td>
                                <#assign sum  = 0 >
                                <#if (flag?boolean == true)>
                                    <#assign sum = oneData.languageScore+oneData.mathScore+oneData.englishScore
                                    +oneData.physicsScore+oneData.chemicalScore+oneData.biologyScore>
                                <#else>
                                    <#assign sum = oneData.languageScore+oneData.mathScore+oneData.englishScore
                                    +oneData.politicsScore+oneData.historyScore+oneData.geographyScore>
                                </#if>
                                <#if sum?exists>
                                    ${(sum)!}
                                </#if>
                            </td>
                            <td>${(oneData.languageScore)!""}</td>
                            <td>${(oneData.mathScore)!""}</td>
                            <td>${(oneData.englishScore)!""}</td>
                            <#if (flag?boolean == true)>
                                <td>${(oneData.physicsScore)!""}</td>
                                <td>${(oneData.chemicalScore)!""}</td>
                                <td>${(oneData.biologyScore)!""}</td>
                            <#else >
                                <td>${(oneData.politicsScore)!""}</td>
                                <td>${(oneData.historyScore)!""}</td>
                                <td>${(oneData.geographyScore)!""}</td>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <div style="margin-top: 10px;">
                    <h3>其他说明：</h3>
                    <p>
                        <span>
                            测试模版！
                        </span>
                    </p>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
