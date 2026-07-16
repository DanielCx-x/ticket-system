-- KEYS[1] = 库存 key，例如 ticket:stock:1
-- ARGV[1] = 回滚数量，例如 2

local count = tonumber(ARGV[1])

redis.call('INCRBY', KEYS[1], count)

return 1