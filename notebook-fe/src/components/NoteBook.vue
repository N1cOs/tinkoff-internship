<template>
  <el-container v-loading.fullscreen.lock="screenLock">
    <el-main>
      <el-card class="header">Создать заметку</el-card>
      <el-card class="form">
        <el-form :model="form" :rules="rules" :ref="formName">
          <el-form-item class="title" prop="title">
            <el-input type="textarea" autosize placeholder="Введите название" v-model="form.title"></el-input>
          </el-form-item>

          <el-form-item prop="date">
            <el-date-picker
              style="width: 100%;"
              v-model="form.date"
              type="datetime"
              placeholder="Выберите дату и время"
            ></el-date-picker>
          </el-form-item>

          <el-form-item prop="text">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 6}"
              placeholder="Введите текст"
              v-model="form.text"
            ></el-input>
          </el-form-item>

          <el-form-item class="create-button">
            <el-button type="primary" class="create-button" @click="onSubmit(formName)">Создать</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card class="header">Мои заметки</el-card>
      <div v-if="records.length > 0" class="sorting-buttons">
        <el-button type="text" class="create-button" @click="sortDesc()">Сначала новые</el-button>
        <el-button type="text" class="create-button" @click="sortAsc()">Сначала старые</el-button>
      </div>
      <el-card v-for="(record, i) in records" :key="record.id" class="note">
        <div v-show="!isEdit[i]">
          <div class="info">
            <div class="note-title">{{record.title}}</div>
            <div class="note-text">{{record.text}}</div>
            <div class="note-date">{{getStringDate(record.date)}}</div>
          </div>
          <div class="buttons">
            <el-button icon="el-icon-edit" @click="editNote(record, i)" circle></el-button>
            <el-button icon="el-icon-delete" @click="deleteNote(record.id, i)" circle></el-button>
          </div>
        </div>
        <div v-show="isEdit[i]">
          <el-form :model="updateForm[i]" :rules="rules" :ref="updateFormName">
            <el-form-item class="title" prop="title">
              <el-input
                type="textarea"
                autosize
                placeholder="Введите название"
                v-model="updateForm[i].title"
              ></el-input>
            </el-form-item>

            <el-form-item prop="date">
              <el-date-picker
                style="width: 100%;"
                v-model="updateForm[i].date"
                type="datetime"
                placeholder="Выберите дату и время"
              ></el-date-picker>
            </el-form-item>

            <el-form-item prop="text">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 6}"
                placeholder="Введите текст"
                v-model="updateForm[i].text"
              ></el-input>
            </el-form-item>
            <el-row :gutter="20">
              <el-col :md="12" :sm="24" style="text-align: end;">
                <el-button type="primary" @click="updateNote(record, i)">Обновить</el-button>
              </el-col>
              <el-col :md="12" :sm="24" style="text-align: start;">
                <el-form-item>
                  <el-button @click="cancelUpdate(i)">Отменить</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>
<script>
import axios from "axios";

export default {
  name: "NoteBook",
  data: function() {
    var validateString = (rule, value, callback) => {
      if (value.trim() == "") {
        callback(new Error(rule.message));
      } else {
        callback();
      }
    };
    var validateDate = (rule, value, callback) => {
      if (value == "") {
        callback(new Error(rule.message));
      } else {
        callback();
      }
    };
    return {
      isEdit: [],
      form: {
        title: "",
        text: "",
        date: ""
      },
      updateForm: [],
      formName: "noteForm",
      updateFormName: "updateForm",
      screenLock: false,
      apiUrl: "/api/record",
      records: [],
      rules: {
        title: [
          {
            validator: validateString,
            message: "Название не может быть пустым",
            trigger: "blur"
          },
          {
            max: 150,
            message: "Название не может быть длинеее 150 символов",
            trigger: "blur"
          }
        ],
        date: [
          { validator: validateDate, message: "Выберите дату", trigger: "blur" }
        ],
        text: [
          {
            validator: validateString,
            message: "Текст заметки не может быть пустым",
            trigger: "blur"
          },
          {
            max: 700,
            message: "Длина текста не может быть более 700 символов",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    onSubmit: function(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.screenLock = true;
          let date = this.getStringDate(this.form.date);

          axios
            .post(this.apiUrl, {
              title: this.form.title,
              text: this.form.text,
              date: date
            })
            .then(r => {
              r.data.date = this.form.date;
              this.records.push(r.data);
              this.isEdit.push(false);
              this.updateForm.push({
                title: "",
                text: "",
                date: ""
              });

              this.resetFields();
              this.$forceUpdate();
              this.screenLock = false;
              this.$message({
                message: "Заметка успешно добавлена",
                type: "success"
              });
            })
            .catch(() => {
              this.$message.error("Упс, что-то пошло не так");
              this.screenLock = false;
              this.resetFields();
            });
        } else {
          return false;
        }
      });
    },
    resetFields: function() {
      this.$refs[this.formName].resetFields();
    },
    editNote: function(record, i) {
      this.isEdit[i] = true;
      this.updateForm[i].title = record.title;
      this.updateForm[i].text = record.text;
      this.updateForm[i].date = record.date;
      this.$forceUpdate();
    },
    updateNote: function(record, i) {
      this.$refs[this.updateFormName][i].validate(valid => {
        if (valid) {
          this.screenLock = true;

          let date = this.getStringDate(this.updateForm[i].date);
          axios
            .put(this.apiUrl, {
              id: record.id,
              title: this.updateForm[i].title,
              text: this.updateForm[i].text,
              date: date
            })
            .then(r => {
              r.data.date = this.updateForm[i].date;
              this.records[i] = r.data;
              
              this.isEdit[i] = false;
              this.$message({
                message: "Заметка успешно обновлена",
                type: "success"
              });
              this.screenLock = false;
            })
            .catch(() => {
              this.$message.error("Упс, что-то пошло не так");
              this.isEdit[i] = false;
              this.screenLock = false;
            });
        } else {
          return false;
        }
      });
    },
    cancelUpdate: function(index) {
      this.isEdit[index] = false;
      this.$forceUpdate();
    },
    deleteNote: function(id, index) {
      this.screenLock = true;
      axios
        .delete(`${this.apiUrl}/${id}`)
        .then(() => {
          this.records = this.records.filter(e => e.id != id);
          this.isEdit.splice(index, 1);
          this.updateForm.splice(index, 1);
          this.$message({
            message: "Заметка успешно удалена",
            type: "success"
          });
          this.screenLock = false;
        })
        .catch(() => {
          this.$message.error("Упс, что-то пошло не так");
          this.screenLock = false;
        });
    },
    getStringDate: function(date) {
      let zeroes = n => {
        if (n <= 9) {
          return "0" + n;
        }
        return n;
      };
      return (
        `${zeroes(date.getHours())}:${zeroes(date.getMinutes())} ` +
        `${zeroes(date.getDate())}-${zeroes(
          date.getMonth() + 1
        )}-${date.getFullYear()}`
      );
    },
    getDateFromString: function(dateStr) {
      //time, day, month, year
      let date = dateStr.split(/-|\s/);
      let time = date[0].split(":");
      return new Date(date[3], date[2] - 1, date[1], time[0], time[1]);
    },
    sortAsc: function() {
      this.records.sort((a, b) => a.date - b.date);
    },
    sortDesc: function() {
        this.records.sort((a, b) => b.date - a.date);
    }
  },
  mounted: function() {
    axios
      .get(this.apiUrl)
      .then(r => {
        this.records = r.data;
        this.records.forEach(rec => {
          rec.date = this.getDateFromString(rec.date);

          this.isEdit.push(false);
          this.updateForm.push({
            title: "",
            text: "",
            date: ""
          });
        });
      })
      .catch(() => {
        this.$message.error("Упс, что-то пошло не так");
      });
  }
};
</script>

<style scoped>
.header {
  color: #409eff;
  font-size: 1.1em;
  font-weight: bold;
  text-align: center;
}

.form {
  margin-bottom: 30px;
  margin-top: 30px;
}

.title {
  margin-bottom: 25px;
}

.create-button {
  text-align: center;
  margin-bottom: -5px;
}

.sorting-buttons {
  text-align: end;
  margin-top: 15px;
  margin-bottom: -15px;
}

.note {
  margin-top: 20px;
  margin-bottom: 20px;
}

.note-title {
  color: #409eff;
  font-size: 1.1em;
  margin-bottom: 5px;
}

.note-text {
  font-size: 0.9em;
  color: #909399;
  font-weight: bold;
}

.note-date {
  margin-top: 15px;
  margin-bottom: 15px;
  font-size: 0.75em;
  color: #909399;
}

.info {
  float: left;
  width: 80%;
}

.buttons {
  float: right;
  text-align: end;
  width: 20%;
}
</style>
