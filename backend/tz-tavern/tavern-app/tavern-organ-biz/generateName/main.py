from config import min_stroke_count, max_stroke_count, allow_general, name_validate, gender, \
    check_name, check_name_resource
from name_set import check_resource, get_source
from wuge import check_wuge_config, get_stroke_list
import sys

name_source = sys.argv[1]
last_name = sys.argv[2]
area_name = sys.argv[3]
industry_name = sys.argv[4]
prefer_word = sys.argv[5]
is_two_word = sys.argv[6]
dislike_words = sys.argv[7]
print("参数1：" +sys.argv[1])
print("参数2：" +sys.argv[2])
print("参数3：" +sys.argv[3])
print("参数4：" +sys.argv[4])
print("参数5：" +sys.argv[5])
print("参数6：" +sys.argv[6])
print("参数7：" +sys.argv[7][0]+sys.argv[7][1])

dislike_words = []
for i in range(1, len(sys.argv[7])):
  dislike_words.append(sys.argv[7][i].replace(",", ""))
print(dislike_words)
dislike_words[0] = dislike_words[0].replace("[", "")
dislike_words[len(sys.argv[7]) - 2] = dislike_words[len(sys.argv[7]) - 2].replace("]", "")
# 字符串数组
print(dislike_words)



def contain_bad_word(first_name):
    for word in first_name:
        if word in dislike_words:
            return True
    return False

def contain_preder_word(first_name):
    print("参数：" +sys.argv[1])
    for word in first_name:
        if word in sys.argv[1]:
            return True
    return False


if len(check_name) == 3:
    # 查看姓名配置
    check_wuge_config(check_name)
    if check_name_resource:
        check_resource(check_name)
    #print(">>输出完毕")
else:
    # 起名
    names = list()
    #with open("names.txt", "w+", encoding='utf-8') as f:
    for i in get_source(name_source, name_validate, get_stroke_list(area_name+industry_name, allow_general)):
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
                if is_two_word:
                    # 不喜欢字过滤
                    continue
            names.append(i)
      #   print(">>输出结果...")
    names.sort()
    for i in names:
            if is_two_word:
            # f.write(area_name+str(i.first_name)+industry_name + "\t"+i.first_name[0] + "\t" + \
            #   i.first_name[1] + "\t" + \
            #    str(i.stroke_number1) + "\t" + str(i.stroke_number2) + "\t" + \
            #    i.source + "\n")
              print(area_name+str(i.first_name)+industry_name + "@"+i.first_name[0] + "@" + \
                                                 i.first_name[1] + "@" + \
                                                 str(i.stroke_number1) + "@" + str(i.stroke_number2) + "@" + \
                                                 i.source + "\n")
            else:
              #f.write(area_name+prefer_word+str(i.first_name)+industry_name +"\t"+i.first_name[0] + "\t" + \
              #  i.first_name[1] + "\t" + \
              #  str(i.stroke_number1) + "\t" + str(i.stroke_number2) + "\t" + \
               # i.source + "\n")
              print(area_name+prefer_word+str(i.first_name)+industry_name +"@"+i.first_name[0] + "@" + \
                                                 i.first_name[1] + "@" + \
                                                 str(i.stroke_number1) + "@" + str(i.stroke_number2) + "@" + \
                                                 i.source + "\n")

              print(">>输出完毕，请查看「names.txt」文件")
