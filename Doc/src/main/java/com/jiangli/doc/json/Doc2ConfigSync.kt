package com.jiangli.doc.json

import com.jiangli.common.utils.HttpPostUtil
import net.sf.json.JSONArray
import net.sf.json.JSONObject


/**
 *
 *
 * @author Jiangli
 * @date 2020/8/26 17:10
 */
fun main(args: Array<String>) {



//    var url = "http://120.92.138.210:20010/web/doc/document/saveOrUpdate"
    var content = getContent()

    val arr = JSONArray.fromObject(content)
//    println(arr)

    processJSONArr(arr!!)

    println(arr.toString())
}


private fun queryListForId(navName:String):Int? {
    //    预发
    var query_doc_url = "http://120.92.138.210:20050/web/doc/document/list"

    val jsonObject = JSONObject()

    jsonObject.put("name",navName)
    jsonObject.put("desc","inf v1")

    val ret = HttpPostUtil.postUrl("""
$query_doc_url
                """.trimIndent(), jsonObject)

//    val arr = JSONArray.fromObject(ret)
    val json = JSONObject.fromObject(ret)
    try {
        val records =json.getJSONObject("rt").getJSONArray("records")
        if (records.size == 1) {
            return records.getJSONObject(0).getInt("id")
        } else {
            println("cant determine for $navName ${records.size} $records")
        }
    } catch (e: Exception) {
        println(json)
        e.printStackTrace()
    }
    return null
}
private fun processJSONArr(arr: JSONArray) {

    arr.forEach {
        val jsonObject = it as JSONObject
        try {
            val jsonArray = safeGetArray(jsonObject,"children")
            val docsId = safeGetString(jsonObject, "docsId")
            val navName = safeGetString(jsonObject, "navName")
            if (jsonArray.size == 0 &&  docsId.isBlank()) {
                val queryListForId = queryListForId(navName)
                println("$navName $queryListForId")
                it.put("docsId","$queryListForId")
            }
            if (jsonArray.size > 0) {
                processJSONArr(jsonArray)
            }
        } catch (e: Exception) {
            println("error $jsonObject")
            e.printStackTrace()
        }
    }
}

private fun safeGetString(jsonObject: JSONObject, s: String):String{
    if (jsonObject.contains(s)) {
        return jsonObject.getString(s)
    }
    return ""
}
private fun safeGetArray(jsonObject: JSONObject, s: String):JSONArray{
    if (jsonObject.contains(s)) {
        return jsonObject.getJSONArray(s)
    }
    return JSONArray()
}


private fun getContent():String{
    return """
[
  {
    "id": 12,
    "docsId": "",
    "navName": "产品概要描述",
    "docsName": "",
    "children": [
      {
        "id": 14,
        "navName": "架构介绍",
        "children": [
          
        ],
        "docsId": "425"
      },
      {
        "id": 15,
        "navName": "主要功能",
        "children": [
          
        ],
        "docsId": "426"
      }
    ]
  },
  {
    "id": 3,
    "docsId": "",
    "navName": "快速集成指南",
    "docsName": "",
    "children": [
      {
        "id": 16,
        "navName": "前提条件",
        "children": [
          
        ],
        "name": "前提条件23",
        "docsId": "427"
      },
      {
        "id": 17,
        "navName": "集成方式",
        "children": [
          
        ],
        "docsId": "428"
      }
    ]
  },
  {
    "id": 19,
    "docsId": "",
    "navName": "SDK带UI集成(课程学习)",
    "docsName": "",
    "children": [
      {
        "id": 20,
        "navName": "产品介绍",
        "children": [
          
        ],
        "docsId": "431"
      },
      {
        "id": 21,
        "navName": "通讯流程",
        "children": [
          
        ],
        "docsId": "432"
      },
      {
        "id": 22,
        "navName": "案例介绍",
        "children": [
          
        ],
        "docsId": "433"
      },
      {
        "id": 23,
        "navName": "接入指南(安卓)",
        "children": [
          {
            "id": 24,
            "navName": "引入SDK",
            "children": [
              
            ],
            "docsId": "434"
          },
          {
            "id": 25,
            "navName": "初始化",
            "children": [
              
            ],
            "docsId": "435"
          },
          {
            "id": 26,
            "navName": "设置监听",
            "children": [
              
            ],
            "docsId": "436"
          }
        ]
      },
      {
        "id": 27,
        "navName": "接入指南(ios)",
        "children": [
          {
            "id": 28,
            "navName": "引入SDK",
            "children": [
              
            ],
            "docsId": "441"
          },
          {
            "id": 29,
            "navName": "初始化",
            "children": [
              
            ],
            "docsId": "442"
          },
          {
            "id": 30,
            "navName": "设置监听",
            "children": [
              
            ],
            "docsId": "443"
          }
        ]
      }
    ]
  },
  {
    "id": 31,
    "docsId": "",
    "navName": "服务端集成",
    "docsName": "",
    "children": [
      {
        "id": 32,
        "navName": "接入指南",
        "children": [
          {
            "id": 33,
            "navName": "架构介绍",
            "children": [
              
            ],
            "docsId": "437"
          },
          {
            "id": 34,
            "navName": "域名",
            "children": [
              
            ],
            "docsId": "438"
          },
          {
            "id": 35,
            "navName": "接口签名",
            "children": [
              
            ],
            "docsId": "440"
          }
        ]
      },
      {
        "id": 37,
        "navName": "用户管理",
        "children": [
          {
            "id": 44,
            "navName": "注册用户",
            "children": [
              
            ]
          },
          {
            "id": 45,
            "navName": "用户详情",
            "children": [
              
            ]
          }
        ]
      },
      {
        "id": 38,
        "navName": "课程学习",
        "children": [
          {
            "id": 46,
            "navName": "课程基本信息",
            "children": [
              
            ]
          },
          {
            "id": 47,
            "navName": "课程教师列表",
            "children": [
              
            ]
          },
          {
            "id": 48,
            "navName": "查课程章节信息",
            "children": [
              
            ]
          },
          {
            "id": 49,
            "navName": "保存章节进度",
            "children": [
              
            ]
          },
          {
            "id": 50,
            "navName": "查询用户章节学习进度",
            "children": [
              
            ]
          },
          {
            "id": 51,
            "navName": "查章节花语卡",
            "children": [
              
            ]
          },
          {
            "id": 52,
            "navName": "查询水印内容",
            "children": [
              
            ]
          },
          {
            "id": 53,
            "navName": "查询个人课程学习时长",
            "children": [
              
            ]
          }
        ]
      },
      {
        "id": 39,
        "navName": "社区",
        "children": [
          {
            "id": 54,
            "navName": "查课程同学吧列表",
            "children": [
              
            ]
          },
          {
            "id": 55,
            "navName": "查留言下评论列表",
            "children": [
              
            ]
          },
          {
            "id": 56,
            "navName": "查询课程社区配置",
            "children": [
              
            ]
          },
          {
            "id": 57,
            "navName": "发布留言",
            "children": [
              
            ]
          },
          {
            "id": 58,
            "navName": "发布评论",
            "children": [
              
            ]
          },
          {
            "id": 59,
            "navName": "删除留言",
            "children": [
              
            ]
          },
          {
            "id": 60,
            "navName": "删除评论",
            "children": [
              
            ]
          },
          {
            "id": 61,
            "navName": "点赞/取消点赞 留言",
            "children": [
              
            ]
          },
          {
            "id": 62,
            "navName": "点赞/取消点赞 评论",
            "children": [
              
            ]
          },
          {
            "id": 63,
            "navName": "点赞/取消点赞 课程",
            "children": [
              
            ]
          },
          {
            "id": 64,
            "navName": "判断用户是否点赞课程",
            "children": [
              
            ]
          },
          {
            "id": 65,
            "navName": "课程互动数量统计",
            "children": [
              
            ]
          }
        ]
      },
      {
        "id": 40,
        "navName": "其它接口",
        "children": [
          {
            "id": 41,
            "navName": "视频播放",
            "children": [
              
            ]
          },
          {
            "id": 42,
            "navName": "视频播放计数",
            "children": [
              
            ]
          },
          {
            "id": 43,
            "navName": "批量上传图片",
            "children": [
              
            ]
          }
        ]
      }
    ]
  },
  {
    "id": 36,
    "docsId": "430",
    "navName": "状态码",
    "docsName": ""
  }
]
    """.trimIndent()
}