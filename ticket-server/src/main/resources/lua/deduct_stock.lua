-- KEYS[1] = 库存 key，例如 ticket:stock:1
-- ARGV[1] = 扣减数量，例如 2

local stock = redis.call('GET', KEYS[1])

if not stock then
    return -1
end

stock = tonumber(stock)
local count = tonumber(ARGV[1])

if stock < count then
    return 0
end

redis.call('DECRBY', KEYS[1], count)

return 1