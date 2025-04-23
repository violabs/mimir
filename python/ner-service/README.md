
# Requirements

python 3.12+

```shell
which python3.12
```

```shell
/opt/homebrew/bin/python3.12 -m venv .venv
```

```shell
source .venv/bin/activate
```

```shell
pip install -r requirements.txt
```

```shell
docker buildx build --platform linux/amd64,linux/arm64 -t violabs/nermal:0.0.1 --push .
```

```shell
docker buildx build --platform linux/amd64,linux/arm64 -t violabs/nermal-md:0.0.2 --push .
```

```shell
docker buildx build --platform linux/amd64,linux/arm64 -t violabs/nermal-lg:0.0.2 --push .
```

```shell
python ./app/app.py
```