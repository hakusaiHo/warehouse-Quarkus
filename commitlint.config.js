module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'type-enum': [2, 'always', [
      'feat',   // 新增/修改功能 (feature)
      'fix',   // bug 修復
      'docs',   // 修改設定文件
      'style',   // 不影響程式碼含義的更改 (空格, 格式, 缺少分號等)
      'refactor',   // 重構 (既不是新增功能，也不是修補 bug 的程式碼變動)
      'perf',   // 改善程式效能的更動
      'test',   // 程式測試
      'chore',   // 建構程序或輔助工具的變動
      'revert'  // 撤銷回覆先前的 commit 例如：revert: type(scope): subject (回覆版本：xxxx)
    ]],
    'header-max-length': [2, 'always', 30],
    'body-min-length': [2, 'always', 10] 
};