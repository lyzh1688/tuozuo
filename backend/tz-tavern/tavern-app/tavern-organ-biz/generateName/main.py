import io
import sys
sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding='gb18030')
from stroke_number import get_stroke_number
from config import name_source, last_name, dislike_words, \
    min_stroke_count, max_stroke_count, allow_general, name_validate, gender, \
    check_name, check_name_resource, area_name, industry_name, prefer_word, is_two_word
from name_set import check_resource, get_source
from wuge import check_wuge_config, get_stroke_list

def to_bool(value):
  if value in ("yes","y","true","True","t","1"): return True
  if value in ("no","n","false","False","f","0","0.0","","none","[]","{}"): return False

def contain_bad_word(first_name):
    for word in first_name:
        if word in dislike_words:
            return True
    return False

def contain_preder_word(first_name):
    for word in first_name:
        if word in sys.argv[4]:
            return True
    return False


if len(check_name) == 3:
    # 查看姓名配置
    check_wuge_config(check_name)
    if check_name_resource:
        check_resource(check_name)
    print(">>输出完毕")
else:
    # 起名
    names = list()
    #print(sys.argv[1])
    #with open("names.txt", "w+", encoding='utf-8') as f:
    for i in get_source(int(sys.argv[1]), name_validate, get_stroke_list(sys.argv[3], allow_general)):
        if i.stroke_number1 < min_stroke_count or i.stroke_number1 > max_stroke_count or \
                i.stroke_number2 < min_stroke_count or i.stroke_number2 > max_stroke_count:
            # 笔画数过滤
            continue
        if name_validate and gender != "" and i.gender != gender and i.gender != "双" and i.gender != "未知":
            # 性别过滤
            continue
        if contain_bad_word(i.first_name):
            # 不喜欢字过滤
            continue
        if (not contain_preder_word(i.first_name)):
            if to_bool(sys.argv[5]):
                # 不喜欢字过滤
                continue
        names.append(i)
    #print(">>输出结果...")
    names.sort()
    for i in names:
        #print(">>i: "+ str(i))
        if to_bool(str(sys.argv[5])):
            #f.write(sys.argv[2]+str(i.first_name)+sys.argv[3] + "\t"+i.first_name[0] + "\t" + \
           #i.first_name[1] + "\t" + \
           #str(get_stroke_number(sys.argv[4])) + "\t" + \
           #str(i.stroke_number1) + "\t" + str(i.stroke_number2) + "\t" + \
           #i.source + "\n")
          #print("分支：" + to_bool(str(sys.argv[5]))
          print(sys.argv[2]+str(i.first_name)+sys.argv[3]+sys.argv[6] + "@"+i.first_name[0] +i.first_name[1] + "@" + \
          str(i.stroke_number1) + "@" + str(i.stroke_number2) + "@" + \
          i.source + "\n")

        else:
           #f.write(sys.argv[2]+sys.argv[4]+str(i.first_name)+sys.argv[3] +"@"+i.first_name[0] + "\t" + \
           #i.first_name[1] + "\t" + \
           #str(get_stroke_number(sys.argv[4])) + "\t" + \
           #str(i.stroke_number1) + "\t" + str(i.stroke_number2) + "\t" + \
           #i.source + "\n")
          print(sys.argv[2]+sys.argv[4]+str(i.first_name)+sys.argv[3]+sys.argv[6] +"@"+sys.argv[4]+str(i.first_name) + "@" + \
          str(get_stroke_number(sys.argv[4])) + "@" + \
          str(i.stroke_number1) + "@" + str(i.stroke_number2) + "@" + \
          i.source + "\n")
        #print(">>输出完毕，请查看「names.txt」文件")
