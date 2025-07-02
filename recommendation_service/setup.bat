 @echo off
echo ===== 校园二手商品推荐系统启动脚本 =====

:: 检查Python环境
python --version
if %ERRORLEVEL% NEQ 0 (
    echo Python未安装，请先安装Python 3.8+
    pause
    exit /b
)

:: 设置Python路径
set PYTHONPATH=%PYTHONPATH%;%CD%

:: 安装依赖
echo 正在安装依赖...
pip install -r requirements.txt

if %ERRORLEVEL% NEQ 0 (
    echo 依赖安装失败！请检查错误信息
    pause
    exit /b
)

:: 启动服务
echo 依赖安装成功！正在启动推荐服务...
echo 服务启动后，可通过浏览器访问 http://localhost:8000/docs 查看API文档

:: 启动FastAPI应用
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload

pause