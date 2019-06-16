network-dir	:=	network
network-file	:=	$(network-dir)/network.name
password-file	:=	$(network-dir)/password
miner-threads	:=	1
ledger-srv-port	:=	6865
test-nav-port	:=	7500
dist-dir	:=	dist


# Ethereum bits
ethereum-setup-nodes:
	@mkdir -pv $(network-dir)
	@echo "password" > $(password-file)
	@for node in $(shell seq 0 3) ; do \
		geth init genesis.json --datadir $(network-dir)/node$${node} ; \
		geth --datadir $(network-dir)/node$${node} account new --password $(password-file) ; \
	done

ethereum-run-network:
	@for node in $(shell seq 0 3) ; do \
		bash -c "trap 'trap - SIGINT SIGTERM ERR; echo Shutdown.; exit 0' SIGINT SIGTERM ERR; \
			geth --datadir $(network-dir)/node$${node} --networkid 2019061016 \
				--port 3030$${node} --miner.threads 1 --rpc --rpcapi 'db,personal,eth,net,web3,debug' \
				--rpccorsdomain='*' --rpcaddr='localhost' --rpcport 854$${node} \
				--mine --miner.threads $(miner-threads) --ipcpath network/node$${node}/geth.ipc \
				--unlock 0x$$(cat $(network-dir)/node$${node}/keystore/UTC-* | head -n1 | jq '.address' | sed -e 's/"//g') \
				--password $(password-file) 2> $(network-dir)/node$${node}.log &" ; \
	done

ethereum-stop-network:
	kill -INT $(shell ps -auf | grep [g]eth | grep 2019061016 | awk '{print $$2}')

ethereum-connect-client:
	geth attach --datadir $(network-dir)/node$(node)

ethereum-cleanup:
	rm -rfv $(network-dir)


# DAML Ledger layer
ledger-dar-build:
	@daml build

ledger-jar-build:
	@sbt compile
	@sbt assembly

ledger-run:	ledger-dar-build	ledger-jar-build
	@bash -c "trap 'trap - SIGINT SIGTERM ERR; echo Shutdown.; exit 0' SIGINT SIGTERM ERR; \
		java -jar target/scala-2.12/damlonx-example.jar --port $(ledger-srv-port) \
			$(dist-dir)/$(shell grep name ./daml.yaml | awk '{print $$2}').dar"

format-check:
	@sbt scalaftmCheckAll

compile: ledger-dar-build ledger-jar-build

test:
	@sbt test

package:
	@sbt assembly

it: package
	bash ./it.sh


# DAML Test layer
daml-test-build:	ledger-dar-build

daml-test-sandbox:
	daml sandbox -w --eager-package-loading $(dist-dir)/$(shell grep name ./daml.yaml | awk '{print $$2}').dar

daml-test-navigator:
	daml navigator server --time wallclock --port $(test-nav-port)
